//package com.ticbus.backend.controller.cms;
//
//import com.ticbus.backend.model.Destination;
//import com.ticbus.backend.payload.request.PageRequest;
//import com.ticbus.backend.payload.response.GetArrayResponse;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/cms/ticket")
//public class TicketCMSController {
//    @PostMapping("/all")
//    public ResponseEntity<GetArrayResponse<Destination>> getAll(@RequestBody PageRequest request) {
//        GetArrayResponse<Destination> res = new GetArrayResponse<>();
//        Page<Destination> page;
////        try {
////            log.info("----begin get all Destination !!");
////            String[] sortSplit = request.getSort().split(",");
////            page = destinationRepository.findAll(
////                    new org.springframework.data.domain.PageRequest(request.getPage(), request.getSize(),
////                            (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC),
////                            sortSplit[0]));
////
////            res.setSuccess(page.getContent(), page.getTotalElements());
////        } catch (Exception e) {
////            log.error("Failed to all Destination : " + e.getMessage());
////            res.setServerError();
////        }
////        log.info("end get all Destination !! ----");
//        return ResponseEntity.ok(res);
//    }
//}
