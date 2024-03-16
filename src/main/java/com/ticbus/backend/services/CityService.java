package com.ticbus.backend.services;

import com.ticbus.backend.common.enumeration.EnumStatus;
import com.ticbus.backend.exception.EntityType;
import com.ticbus.backend.exception.ExceptionType;
import com.ticbus.backend.exception.TicbusException;
import com.ticbus.backend.model.City;
import com.ticbus.backend.model.Departure;
import com.ticbus.backend.model.Destination;
import com.ticbus.backend.model.specification.CitySpecification;
import com.ticbus.backend.payload.request.CityListRequest;
import com.ticbus.backend.repository.CityRepository;
import com.ticbus.backend.repository.DepartureRepository;
import com.ticbus.backend.repository.DestinationRepository;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CityService {

  @Autowired
  private CityRepository cityRepository;

  @Autowired
  private CitySpecification citySpecification;

  @Autowired
  private DepartureRepository departureRepository;

  @Autowired
  private DestinationRepository destinationRepository;

  public Page<City> getAllCities(CityListRequest request) {
    String[] sortSplit = request.getSort().split(",");
    if (!Objects.isNull(request.getSearch())) {
      return cityRepository
          .findAll(CitySpecification.textInAllColumns(request.getSearch()),
              new org.springframework.data.domain.PageRequest(request.getPage(),
                  request.getSize(),
                  (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                      : Sort.Direction.DESC), sortSplit[0]));
    } else {
      return new PageImpl<>(cityRepository
          .findAll(citySpecification.getFilter(request),
              new org.springframework.data.domain.PageRequest(request.getPage(),
                  request.getSize(),
                  (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                      : Sort.Direction.DESC), sortSplit[0])).stream().filter(distinctByKey(City::getProvince)).collect(Collectors.toList()));

    }
  }

  @Cacheable(value = "city", key = "#id", unless = "#result != null")
  public City findCityById(Integer id) {
    return cityRepository.findCityById(id)
        .orElseThrow(() -> exception(EntityType.CITY, ExceptionType.ENTITY_NOT_FOUND,
            String.valueOf(id)));
  }

  @CacheEvict(value = "city", key = "#id", condition = "#status.id == 3")
  public City changeStatus(Integer id, EnumStatus status) {
    return cityRepository.findCityById(id).map(city -> {
      city.setStatus(status.getId());
      return cityRepository.save(city);
    }).orElseThrow(
        () -> exception(EntityType.CITY, ExceptionType.ENTITY_NOT_FOUND, String.valueOf(id)));
  }

  public List<Departure> getDepartureByCity(Integer id) {
    if (cityRepository.existsById(id)) {
      return departureRepository.findByCity_Id(id);
    }
    throw exception(EntityType.CITY, ExceptionType.ENTITY_NOT_FOUND, String.valueOf(id));
  }

  public List<Destination> getDestinationByCityId(Integer id) {
    if (cityRepository.existsById(id)) {
      return destinationRepository.findByCity_Id(id);
    }
    throw exception(EntityType.CITY, ExceptionType.ENTITY_NOT_FOUND, String.valueOf(id));
  }

  public City settingIndexWithCityId(Integer cityId, Integer indexId) {
    if (cityRepository.existsById(cityId)) {
      Optional<City> optCity = cityRepository.findCityById(cityId);
      optCity.get().setIndex(indexId);
      return cityRepository.save(optCity.get());
    }
    throw exception(EntityType.CITY, ExceptionType.ENTITY_NOT_FOUND, String.valueOf(cityId));
  }

  public City updateIndexByCityId(Integer cityId, Integer indexId) {
    if (cityRepository.existsById(cityId)) {
      Optional<City> optCity = cityRepository.findCityById(cityId);
      optCity.get().setIndex(indexId);
      return cityRepository.save(optCity.get());
    }
    throw exception(EntityType.CITY, ExceptionType.ENTITY_NOT_FOUND, String.valueOf(cityId));
  }

  /**
   * Returns a new RuntimeException
   */
  private RuntimeException exception(EntityType entityType, ExceptionType exceptionType,
      String... args) {
    return TicbusException.throwException(entityType, exceptionType, args);
  }

  /**
   * Returns a new RuntimeException
   */
  private RuntimeException exceptionWithId(EntityType entityType, ExceptionType exceptionType,
      String id, String... args) {
    return TicbusException.throwExceptionWithId(entityType, exceptionType, id, args);
  }

  public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
    Map<Object, Boolean> map = new ConcurrentHashMap<>();
    return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }
}
