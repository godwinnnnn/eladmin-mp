/*
*  Copyright 2019-2023 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.gen.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.gen.domain.Test;
import me.zhengjie.gen.service.TestService;
import me.zhengjie.gen.domain.vo.TestQueryCriteria;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.zhengjie.utils.PageResult;

/**
* @author goodwin
* @date 2023-07-19
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "测试生成管理")
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('test:list')")
    public void exportTest(HttpServletResponse response, TestQueryCriteria criteria) throws IOException {
        testService.download(testService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询测试生成")
    @ApiOperation("查询测试生成")
    @PreAuthorize("@el.check('test:list')")
    public ResponseEntity<PageResult<Test>> queryTest(TestQueryCriteria criteria, Page<Object> page){
        return new ResponseEntity<>(testService.queryAll(criteria,page),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增测试生成")
    @ApiOperation("新增测试生成")
    @PreAuthorize("@el.check('test:add')")
    public ResponseEntity<Object> createTest(@Validated @RequestBody Test resources){
        testService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改测试生成")
    @ApiOperation("修改测试生成")
    @PreAuthorize("@el.check('test:edit')")
    public ResponseEntity<Object> updateTest(@Validated @RequestBody Test resources){
        testService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除测试生成")
    @ApiOperation("删除测试生成")
    @PreAuthorize("@el.check('test:del')")
    public ResponseEntity<Object> deleteTest(@RequestBody List<Long> ids) {
        testService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}