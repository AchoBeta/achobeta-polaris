package com.achobeta.api;

import com.achobeta.api.dto.RenderRequestDTO;
import com.achobeta.api.dto.RenderResponseDTO;
import com.achobeta.types.Response;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author BanTanger 半糖
 * @date 2024/11/4
 */
public interface IReadService {

//    Response<String> read(ReadRequestDTO readRequestDTO);

    Response<RenderResponseDTO> render(@Valid RenderRequestDTO readRequestDTO);

    ResponseEntity<String> body(@RequestParam("input") @Min(5) Integer input);

}
