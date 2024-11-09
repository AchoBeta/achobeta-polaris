package com.achobeta.domain.device.service.get;

import com.achobeta.domain.device.adapter.repository.IDeviceRepository;
import com.achobeta.domain.device.model.bo.DeviceBO;
import com.achobeta.domain.device.model.entity.DeviceEntity;
import com.achobeta.domain.device.model.entity.PageResult;
import com.achobeta.domain.device.model.valobj.DeviceVO;
import com.achobeta.domain.device.service.IDeviceTextService;
import com.achobeta.types.common.Constants;
import com.achobeta.types.support.postprocessor.AbstractPostProcessor;
import com.achobeta.types.support.postprocessor.PostContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * @author huangwenxing
 * @description 默认设备渲染服务实现类
 * @date 2024/11/4
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGetDeviceService extends AbstractPostProcessor<DeviceBO> implements IDeviceTextService {

    private final IDeviceRepository repository;
    @Override
    public PostContext<DeviceBO> doMainProcessor(PostContext<DeviceBO> postContext) {

        Map<String, Object> extra = postContext.getExtra();
        DeviceEntity device = (DeviceEntity)extra.get("device");
        PageResult page = (PageResult) extra.get("page");

        List<DeviceEntity> devices = repository.getDevices(device.getUserId(),page.getLimit(), page.getLastDeviceId());
        //与前端传来的设备id比较，确认是否为登陆设备
        for (DeviceEntity deviceEntity : devices) {
            if (device.getDeviceId().equals(deviceEntity.getDeviceId())){
                deviceEntity.setMe(true);
            }

        }

        postContext.setBizData(DeviceBO.builder()
                .deviceEntities(devices)
                .build());
        return postContext;
    }

    @Override
    public List<DeviceVO> getDeviceVO(String userId,String deviceId,int limit,String lastDeviceId)  {
        PostContext<DeviceBO> postContext = buildPostContext(userId,deviceId,limit,lastDeviceId);

        postContext = super.doPostProcessor(postContext, GetDevicePostProcessor.class);

        DeviceBO bizData = postContext.getBizData();
        List<DeviceEntity> deviceEntities = bizData.getDeviceEntities();

        return deviceEntities.stream().map(deviceEntity -> DeviceVO.builder()
                .deviceId(deviceEntity.getDeviceId())
                .deviceName(deviceEntity.getDeviceName())
                .lastLoginTime(deviceEntity.getLastLoginTime())
                .IP(deviceEntity.getIP())
                .me(deviceEntity.isMe())
                .build()).collect(Collectors.toList());
    }

    private static PostContext<DeviceBO> buildPostContext(String userId,String deviceid,int limit,String lastDeviceId) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("device", DeviceEntity.builder().userId(userId).deviceId(deviceid).build());
        map.put("page", PageResult.builder().limit(limit).lastDeviceId(lastDeviceId).build());

        return PostContext.<DeviceBO>builder()
                .bizName(Constants.BizModule.DEVICE.getName())
                .bizData(DeviceBO.builder().build())
                .extra(map)
                .build();
    }
}
