package com.fieldmanagement.fieldmanagement_be.model.entity;

import com.fieldmanagement.commom.model.entity.ImageBaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@Entity(name = "ImageModel")
@Table(name = "images")
public class ImageModel extends ImageBaseModel {
}
