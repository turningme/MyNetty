package org.lkpnotice.turnning.mynetty.example.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by liujinpeng on 2019/4/14.
 * 安装包的系统
 * 依赖
 * 卸载
 * 图、树？
 *
 */
public class Design {



    static  class PkgNode{
        String id;//系统内唯一
        String name;
        String[] dependencies;
    }

    interface  Pkgmanager{
        //简单的安装，安装一个包，指定名称，依赖列表
        public void install(String namePkg,String[] dependencies);
        //卸载
        public void uninstall(String namePkg);
        //列表，系统内已经安装的所有包
        public String[] list();

    }


    static abstract class PkgmanagerImpl implements Pkgmanager{
        //安装的所有包的列表
        List<PkgNode> pkgs = null;
        //根据包ID O(1) 复杂度定位包
        Map<String,PkgNode> lookupPkgs = null;

        //翻查表，找到 所有依赖K的下级包
        Map<String,Set> invertedLookup = null;


        @Override
        public void install(String namePkg, String[] dependencies) {
            //安装包 namePkg本身，要先确定依赖是否安装，递归查看所有依赖，确保依赖全部安装

            /**
             * 依赖不为空，需要保证依赖的安装
             */
            if (null != dependencies){
                for (String depend:dependencies
                        ) {
                    if (isInstalled(depend)) continue;

                    //没有安装，那么要递归安装依赖包
                    installDependencies(depend);
                }
            }

            //依赖安装完成后 安装namePkg
            installOnePkgInternally(namePkg,dependencies);

        }

        @Override
        public void uninstall(String namePkg) {
            //卸载namePkg 确定namePkg是否能够被卸载，如果有用户指定的包依赖该包，则不能卸载

        }

        @Override
        public String[] list() {
            return new String[0];
        }



        //内部的一个安装指定包 kk的操作
        abstract void installInternally(String kk);



        //判断包是否已经安装
        private boolean isInstalled(String kk){
            //判断包是否已经安装
            return false;
        }


        //递归安装依赖，直到所有依赖安装完成
        private void installDependencies(String kk){
            //获取 kk的依赖 然后安装
            String[] dependsOfkk = null;

            if (null != dependsOfkk){
            for (String ele:dependsOfkk
                 ) {
                if (!isInstalled(ele)){
                    installDependencies(ele);
                }
            }

                //安装依赖全部安装完，安装kk
                installOnePkgInternally(kk,dependsOfkk);
            }

        }


        private void installOnePkgInternally(String kk, String[] dependencies){
            //更新pkgs lookupPkgs  invertedLookup
        }


    }
}
