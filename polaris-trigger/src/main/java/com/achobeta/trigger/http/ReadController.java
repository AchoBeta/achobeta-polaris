package com.achobeta.trigger.http;

import com.achobeta.api.IReadService;
import com.achobeta.api.dto.RenderRequestDTO;
import com.achobeta.api.dto.RenderResponseDTO;
import com.achobeta.domain.render.model.valobj.RenderBookVO;
import com.achobeta.domain.render.service.IRenderTextService;
import com.achobeta.types.Response;
import com.achobeta.types.annotation.Creator;
import com.achobeta.types.annotation.Updater;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BanTanger 半糖
 * @date 2024/11/4
 */
@Slf4j
@Validated
@RestController()
@CrossOrigin("${app.config.cross-origin}:*")
@RequestMapping("/api/${app.config.api-version}/read/")
@RequiredArgsConstructor
public class ReadController implements IReadService {

    private final IRenderTextService renderTextService;

    /**
     * demo 接口，熟悉包结构
     * @param renderRequestDTO
     * @return
     */
    @PostMapping("render")
    @Override
    public Response<RenderResponseDTO> render(@Valid @RequestBody RenderRequestDTO renderRequestDTO) {
        try {
            log.info("用户访问文本渲染系统开始，userId:{} bookId:{}",
                    renderRequestDTO.getUserId(), renderRequestDTO.getBookId());

            RenderBookVO renderBookVO = renderTextService
                    .renderBook(renderRequestDTO.getUserId(), renderRequestDTO.getBookId());

            log.info("用户访问文本渲染系统结束，username:{} bookName:{} bookContent:{}",
                    renderBookVO.getUserName(), renderBookVO.getBookName(), renderBookVO.getBookContent());

            return Response.SYSTEM_SUCCESS(RenderResponseDTO.builder()
                .userName(renderBookVO.getUserName())
                .bookName(renderBookVO.getBookName())
                .bookContent(renderBookVO.getBookContent())
                .build());
        } catch (Exception e) {
            log.error("用户访问文本渲染系统失败！userId:{} bookId:{}",
                    renderRequestDTO.getUserId(), renderRequestDTO.getBookId(), e);
            return Response.SERVICE_ERROR(e.getMessage());
        }
    }

    @Override
    @PostMapping("body")
    public ResponseEntity<String> body(@Min(5) Integer input) {
        return ResponseEntity.ok("body");
    }

    /**
     * 模拟更新
     * @param renderRequestDTO
     * @return
     */
    @PostMapping("update")
    public ResponseEntity<String> update(@Validated(Updater.class) @RequestBody RenderRequestDTO renderRequestDTO) {
        return ResponseEntity.ok("update ok");
    }

    /**
     * 模拟创建
     * @param renderRequestDTO
     * @return
     */
    @PostMapping("create")
    public ResponseEntity<String> create(@Validated(Creator.class) @RequestBody RenderRequestDTO renderRequestDTO) {
        return ResponseEntity.ok("create ok");
    }

}
