package com.example.firstdemo.conf.cache.service.impl;

import com.example.firstdemo.conf.cache.service.CacheServiceInit;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.concurrent.TimeUnit;
@CommonsLog
public class CacheServiceInitImpl implements CacheServiceInit, ApplicationRunner {
    public static final String LP_FLOW_ELEMENT_KEY = "LP_FLOW_ELEMENT_KEY";

//    @Autowired
//    private LpChannelCreditCodeRelService lpChannelCreditCodeRelService;
//    @Autowired
//    private CooperativeBusinessDao cooperativeBusinessDao;
//    @Autowired
//    private CooperativeMsgDao cooperativeMsgDao;
//    @Autowired
//    private ChannelConfigurationDao channelConfigurationDao;
//    @Autowired
//    ChannelApiPerDao channelApiPerDao;
//    @Autowired
//    private LpFlowElementService lpFlowElementService;
//    @Autowired
//    private LpFileConfigDao fileConfigDao;
//    @Autowired
//    private CommonConfig commonConfig;
//    @Autowired
//    private LPShedulingDao lpShedulingDao;
//    @Autowired
//    private CheckKeyOfYesOrNoDao checkKeyOfYesOrNoDao;
//    @Autowired
//    private LpRefuseReasonsService lpRefuseReasonsService;
//
//    @Autowired
//    private ErrorCodeInfoService errorCodeInfoService;
//    @Autowired
//    private SalePersonConfigDao salePersonConfigDao;

    @Override
    public void initCacheToRedis() {
        log.info("更新redis数据库缓存定时开始！");
        //数据加载
//        try (LockObject ignored = DistributedLock.lock("initSysParam", 1, TimeUnit.MINUTES)) {
//            initSysParam();
//        } catch (Exception e) {
//            log.info("其他服务器正在执行数据库配置加载至redis操作");
//        }
        log.info("更新redis数据库缓存定时结束！");
    }

    public void initSysParam() {
//        log.info("initSysParam===初始化开始！");
//        List<LpScheduling> lpSchedulings = lpShedulingDao.selectAll();
//        RedisUtils.set("lpSchedulings", lpSchedulings);
//        List<LpFileConfig> lpFileConfigs = fileConfigDao.selectAll();
//        RedisUtils.set("lpFileConfigs", lpFileConfigs);
//        List<CooperativeBusiness> cooperativeBusinesses = this.cooperativeBusinessDao.selectByActiveFlag(BusinessConstance.COOPERATIVEBUSINESS_ACTIVE);
//        RedisUtils.set("cooperativeBusinesses", cooperativeBusinesses);
//        List<CooperativeMsg> cooperativeMsgs = this.cooperativeMsgDao.selectAll();
//        RedisUtils.set("cooperativeMsgs", cooperativeMsgs);
//        List<ChannelConfiguration> channelConfigurations = this.channelConfigurationDao.selectByActiveFlag(BusinessConstance.COOPERATIVEBUSINESS_ACTIVE);
//        RedisUtils.set("channelConfigurations", channelConfigurations);
//        List<ChannelApiPer> ChannelApiPers = this.channelApiPerDao.selectAll();
//        RedisUtils.set("permission", ChannelApiPers);
//        List<LpFlowElement> lpFlowElements = lpFlowElementService.selectAll();
//        if(lpFlowElements != null && !lpFlowElements.isEmpty()){
//            lpFlowElements.forEach(x -> {
//                StringBuilder key = new StringBuilder().append(x.getChannelNo()).append(x.getTransAccNo()).append(x.getTransAccName()).append(x.getRemark());
//                RedisUtils.hset(LP_FLOW_ELEMENT_KEY, key.toString(), true);
//                RedisUtils.hset(LP_FLOW_ELEMENT_KEY, x.getChannelNo(), true);
////                RedisUtils.hexists()
//            });
//        }
//        List<CheckKeyOfYesOrNo> checkKeyOfYesOrNoList = this.checkKeyOfYesOrNoDao.selectAll();
//        RedisUtils.set("checkKeyOfYesOrNo", checkKeyOfYesOrNoList);
//        SalePersonConfig salePersonConfig = new SalePersonConfig();
//        salePersonConfig.setFlag("1");
//        List<SalePersonConfig> SalePersonConfigs = salePersonConfigDao.select(salePersonConfig);
//        RedisUtils.set("SalePersonConfigs", SalePersonConfigs);
//
//        errorCodeInfoService.updateErrorCodeToRedis();
//        lpChannelCreditCodeRelService.putCreditCodeInCache();
        log.info("initSysParam===初始化成功，结束！");
    }

//    @Override
    public void refuseReasonsToRedis() throws Exception {
        log.info("更新redis拒绝原因大类缓存定时开始！");
//        //数据加载
//        try (LockObject ignored = DistributedLock.lock("reasonsParam", 1, TimeUnit.MINUTES)) {
//            reasonsParam();
//        } catch (Exception e) {
//            log.info("其他服务器正在执行拒绝原因大类加载至redis操作");
//        }
        log.info("更新redis拒绝原因大类缓存定时结束！");
    }

    public void reasonsParam(){
        log.info("reasonsParam===初始化开始！");
//        lpRefuseReasonsService.refuseReasonsToRedis();
        log.info("reasonsParam===初始化成功，结束！");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //数据加载
        try {
            log.info("同业交互平台启动，数据库密钥加载至缓存开始！");
            reasonsParam();
            initSysParam();
//            RedisUtils.set("creditWarnStatus", commonConfig.getCreditWarnStatus());
//            RedisUtils.set("useWarnStatus", commonConfig.getUseWarnStatus());
//            RedisUtils.set("creditWarnInterval", commonConfig.getCreditWarnInterval());
//            RedisUtils.set("useWarnInterval", commonConfig.getUseWarnInterval());
            log.info("同业交互平台启动，数据库密钥加载至缓存结束！");
        } catch (Exception e) {
            log.info("执行数据库配置加载至redis初始化操作抛出异常");
            e.printStackTrace();
        }
    }
}
