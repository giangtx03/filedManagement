package com.fieldmanagement.fieldmanagement_be.field.usecase;

import com.fieldmanagement.fieldmanagement_be.common.base.dto.PageResult;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.ImageTargetTypeEnum;
import com.fieldmanagement.fieldmanagement_be.common.helper.MetaDataHelper;
import com.fieldmanagement.fieldmanagement_be.common.helper.SortHelper;
import com.fieldmanagement.fieldmanagement_be.field.adapter.db.dto.FieldEntityDTO;
import com.fieldmanagement.fieldmanagement_be.field.adapter.mapper.FieldMapper;
import com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.request.FieldFilterRequest;
import com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response.FieldResponse;
import com.fieldmanagement.fieldmanagement_be.field.domain.dto.FieldDTO;
import com.fieldmanagement.fieldmanagement_be.field.domain.port.FieldRepository;
import com.fieldmanagement.fieldmanagement_be.image.domain.model.Image;
import com.fieldmanagement.fieldmanagement_be.image.domain.port.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldUseCase {
    private final FieldRepository fieldRepository;
    private final ImageRepository imageRepository;
    private final FieldMapper fieldMapper;

    public PageResult<List<FieldResponse>> getAllFields(FieldFilterRequest filterRequest) {
        Sort sort = SortHelper.buildSort(filterRequest.getOrder(), filterRequest.getDirection());
        Pageable pageable = PageRequest.of(filterRequest.getCurrentPage() - 1, filterRequest.getPageSize(), sort);

        Page<FieldDTO> fieldDTOS = fieldRepository.getAllFields(
                filterRequest.getKeyword(), filterRequest.getLocation(),
                filterRequest.getStartPrice(), filterRequest.getEndPrice(),
                filterRequest.getStartTime(), filterRequest.getEndTime(), pageable
        );

        List<FieldResponse> fieldResponses = fieldDTOS.getContent()
                .stream()
                .map(fieldDTO -> {
                    List<String> images = imageRepository.findAllByTargetIdAndTargetType(
                            fieldDTO.getId(), ImageTargetTypeEnum.FIELD
                    ).stream().map(Image::getPath).toList();
                    fieldDTO.setImages(images);
                    return fieldMapper.toFieldResponse(fieldDTO);
                })
                .toList();

        return PageResult.<List<FieldResponse>>builder()
                .data(fieldResponses)
                .metaData(MetaDataHelper.buildMetaData(fieldDTOS, filterRequest))
                .build();
    }
}
