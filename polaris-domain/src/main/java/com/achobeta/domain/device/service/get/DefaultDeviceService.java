package com.achobeta.domain.device.service.get;

import com.achobeta.domain.device.adapter.repository.IDeviceRepository;
import com.achobeta.domain.device.model.bo.DeviceBO;
import com.achobeta.domain.device.model.entity.DeviceEntity;
import com.achobeta.domain.device.model.entity.PageResult;
import com.achobeta.domain.device.model.entity.UserCommonDevicesEntities;
import com.achobeta.domain.device.model.valobj.UserCommonDevicesVO;
import com.achobeta.domain.device.service.IDeviceService;
import com.achobeta.types.common.Constants;
import com.achobeta.types.enums.BizModule;
import com.achobeta.types.enums.GlobalServiceStatusCode;
import com.achobeta.types.exception.AppException;
import com.achobeta.types.support.postprocessor.AbstractPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author huangwenxing
 * @description 默认设备渲染服务实现类
 * @date 2024/11/8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultDeviceService extends AbstractPostProcessor<DeviceBO> implements IDeviceService {

    private final IDeviceRepository repository;
    @Override
    public PostContext<DeviceBO> doMainProcessor(PostContext<DeviceBO> postContext) {

        UserCommonDevicesEntities userEntities = postContext.getBizData().getUserCommonDevicesEntities();
        PageResult pageResult = postContext.getBizData().getPageResult();
        //查数据库时加将长度加一，确认是否有剩余数据
        List<DeviceEntity> devices = repository.queryCommonUseDevicesById(userEntities.getUserId(),pageResult.getLimit()+1,pageResult.getLastDeviceId());

        if(CollectionUtils.isEmpty(devices)){
            log.error("设备不存在！userId：{},limit:{},lastDeviceId:{}",userEntities.getUserId(),pageResult.getLimit(),pageResult.getLastDeviceId());
            throw new AppException(String.valueOf(GlobalServiceStatusCode.PARAM_NOT_VALID.getCode()),GlobalServiceStatusCode.PARAM_NOT_VALID.getMessage());
        }

        //判断还有没有更多数据
        boolean flag = devices.size() > pageResult.getLimit();
        if(flag){
            //有就移除最后一个并返回给前端
            devices.remove(devices.size()-1);
        }
        //与前端传来的设备id比较，确认是否为登陆设备
        postContext.addExtraData(Constants.NEXT_PAGE,flag);
        devices.stream()
                .filter(deviceEntity -> userEntities.getDeviceId().equals(deviceEntity.getDeviceId()))
                .forEach(deviceEntity -> deviceEntity.setMe(true));

        userEntities.setDeviceEntities(devices);
        return postContext;
    }

    @Override
    public UserCommonDevicesVO queryCommonUseDevicesById(String userId, String deviceId, int limit, String lastDeviceId)  {
        PostContext<DeviceBO> postContext = buildPostContext(userId,deviceId,limit,lastDeviceId);
        postContext = super.doPostProcessor(postContext, DevicePostProcessor.class);
        DeviceBO bizData = postContext.getBizData();
        List<DeviceEntity> deviceEntities = bizData.getUserCommonDevicesEntities().getDeviceEntities();


        return UserCommonDevicesVO.builder()
                .deviceEntities(deviceEntities)
                .more((boolean)postContext.getExtra().get(Constants.NEXT_PAGE))
                .build();
    }

    private static PostContext<DeviceBO> buildPostContext(String userId,String deviceid,int limit,String lastDeviceId) {
        return PostContext.<DeviceBO>builder()
                .bizName(BizModule.DEVICE.getName())
                .bizData(DeviceBO.builder()
                        .userCommonDevicesEntities(UserCommonDevicesEntities.builder()
                                .userId(userId)
                                .deviceId(deviceid)
                                .build())
                        .pageResult(PageResult.builder()
                                .limit(limit)
                                .lastDeviceId(lastDeviceId)
                                .build())
                        .build())
                .build();
    }
}
