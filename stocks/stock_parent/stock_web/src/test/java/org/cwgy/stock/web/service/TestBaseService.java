package org.cwgy.stock.web.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class) // 匯入spring測試框架[2]
@SpringBootTest // 提供spring依賴注入
@Transactional // 事務管理，預設回滾,如果配置了多資料來源記得指定事務管理器
public class TestBaseService {

}
