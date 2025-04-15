package com.fieldmanagement.fieldmanagement_be.field.usecase;

import com.fieldmanagement.fieldmanagement_be.common.base.dto.PageResult;
import com.fieldmanagement.fieldmanagement_be.common.base.enums.ImageTargetTypeEnum;
import com.fieldmanagement.fieldmanagement_be.common.helper.MetaDataHelper;
import com.fieldmanagement.fieldmanagement_be.common.helper.SortHelper;
import com.fieldmanagement.fieldmanagement_be.field.adapter.mapper.FieldMapper;
import com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.request.FieldFilterRequest;
import com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response.FieldDetailResponse;
import com.fieldmanagement.fieldmanagement_be.field.adapter.web.dto.response.FieldResponse;
import com.fieldmanagement.fieldmanagement_be.field.domain.dto.FieldDTO;
import com.fieldmanagement.fieldmanagement_be.field.domain.port.FieldRepository;
import com.fieldmanagement.fieldmanagement_be.field.exception.FieldNotFoundException;
import com.fieldmanagement.fieldmanagement_be.image.domain.port.ImageQueryPort;
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
    private final ImageQueryPort imageQueryPort;
    private final FieldMapper fieldMapper;

    public PageResult<List<FieldResponse>> getAllFields(FieldFilterRequest filterRequest) {
        Sort sort = SortHelper.buildSort(filterRequest.getOrder(), filterRequest.getDirection());
        Pageable pageable = PageRequest.of(filterRequest.getCurrentPage() - 1, filterRequest.getPageSize(), sort);

        Page<FieldDTO> fieldDTOS = fieldRepository.getAllFields(
                filterRequest.getKeyword(), filterRequest.getLocation(),
                filterRequest.getStartPrice(), filterRequest.getEndPrice(),
                filterRequest.getFromTime(), filterRequest.getToTime(), pageable
        );

        List<FieldResponse> fieldResponses = fieldDTOS.getContent()
                .stream()
                .map(this::getAllImages)
                .map(fieldMapper::toFieldResponse)
                .toList();

        return PageResult.<List<FieldResponse>>builder()
                .data(fieldResponses)
                .metaData(MetaDataHelper.buildMetaData(fieldDTOS, filterRequest))
                .build();
    }

    public FieldDetailResponse getFieldDetail(String urlSlug) {
        FieldDTO fieldDTO = fieldRepository.findByUrlSlug(urlSlug)
                .orElseThrow(() -> new FieldNotFoundException("Field not found!"));
        getAllImages(fieldDTO);
        return fieldMapper.toFieldDetailResponse(fieldDTO);
    }

    private FieldDTO getAllImages(FieldDTO fieldDTO) {
        List<String> images = imageQueryPort.getImagePathsByTarget(fieldDTO.getId(), ImageTargetTypeEnum.FIELD);
        fieldDTO.setImages(images);
        return fieldDTO;
    }
}
