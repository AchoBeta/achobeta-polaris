package com.achobeta.domain.device.service.get;

import com.achobeta.domain.device.adapter.repository.IDeviceRepository;
import com.achobeta.domain.device.model.bo.DeviceBO;
import com.achobeta.domain.device.model.entity.DeviceEntity;
import com.achobeta.domain.device.model.entity.PageResult;
import com.achobeta.domain.device.model.entity.UserCommonDevicesEntities;
import com.achobeta.domain.device.model.valobj.UserCommonDevicesVO;
import com.achobeta.domain.device.service.IDeviceService;
import com.achobeta.types.common.Constants;
import com.achobeta.types.support.postprocessor.AbstractPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

        List<DeviceEntity> devices = repository.queryCommonUseDevicesById(userEntities.getUserId(),pageResult.getLimit(),pageResult.getLastDeviceId());
        //与前端传来的设备id比较，确认是否为登陆设备
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
        //判断还有没有更多数据(原则上来讲好像应该放到doMainProcessor，感觉学长不纠结于这些我就放这了 )
        boolean flag = deviceEntities.size() == bizData.getPageResult().getLimit();

        return UserCommonDevicesVO.builder()
                .deviceEntities(deviceEntities)
                .more(flag)
                .build();
    }

    private static PostContext<DeviceBO> buildPostContext(String userId,String deviceid,int limit,String lastDeviceId) {
        return PostContext.<DeviceBO>builder()
                .bizName(Constants.BizModule.DEVICE.getName())
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
