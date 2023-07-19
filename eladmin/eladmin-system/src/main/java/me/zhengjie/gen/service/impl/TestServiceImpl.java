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
package me.zhengjie.gen.service.impl;

import me.zhengjie.gen.domain.Test;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.gen.service.TestService;
import me.zhengjie.gen.domain.vo.TestQueryCriteria;
import me.zhengjie.gen.mapper.TestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import me.zhengjie.utils.PageUtil;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import me.zhengjie.utils.PageResult;

/**
* @description 服务实现
* @author goodwin
* @date 2023-07-19
**/
@Service
@RequiredArgsConstructor
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

    private final TestMapper testMapper;

    @Override
    public PageResult<Test> queryAll(TestQueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(testMapper.findAll(criteria, page));
    }

    @Override
    public List<Test> queryAll(TestQueryCriteria criteria){
        return testMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Test resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Test resources) {
        Test test = getById(resources.getId());
        test.copy(resources);
        saveOrUpdate(test);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<Test> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Test test : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("name", test.getName());
            map.put("remark", test.getRemark());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}