package com.gfk.business.data.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/10/24
 */
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class OptimizerDataContainerBO {
    private List<OptimizerDataCacheBO> dataContainer;
    private Long target;
}
