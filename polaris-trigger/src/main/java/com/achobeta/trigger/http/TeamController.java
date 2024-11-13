package com.achobeta.trigger.http;

import com.achobeta.api.ITeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangzhiyao
 * @date 2024/11/8
 */
@Slf4j
@Validated
@RestController()
@CrossOrigin("${app.config.cross-origin}:*")
@RequestMapping("/api/${app.config.api-version}/team/")
@RequiredArgsConstructor
public class TeamController implements ITeamService {

}
