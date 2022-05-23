package com.teamworker.rest.manager;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping(value = "/api/v1/manager/position")
@Tag(name = "/api/v1/manager/position", description = "Контролер для керування посадами (manager)")
public class PositionManagerRestController {
}
