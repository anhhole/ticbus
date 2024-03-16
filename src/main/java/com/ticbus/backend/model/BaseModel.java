package com.ticbus.backend.model;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

/**
 * @author AnhLH
 */

@Data
@MappedSuperclass
@TypeDefs({
    @TypeDef(name = "string-array", typeClass = StringArrayType.class),
    @TypeDef(name = "int-array", typeClass = IntArrayType.class),
    @TypeDef(name = "json", typeClass = JsonStringType.class),
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
    @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class),
    @TypeDef(name = "json-node", typeClass = JsonNodeStringType.class),
})
class BaseModel {

  @Column(name = "created_time")
  private Date createdTime;

  @Column(name = "last_modified_time")
  private Date lastModifiedDate;
}
