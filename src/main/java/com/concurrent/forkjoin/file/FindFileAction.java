package com.concurrent.forkjoin.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * 遍历指定目录（含子目录）找寻指定类型文件，不需要返回值.
 */
public class FindFileAction extends RecursiveAction {

    private File filePath;

    public FindFileAction(File filePath) {
        this.filePath = filePath;
    }

    /**
     * The main computation performed by this task.
     */
    @Override
    protected void compute() {
        List<FindFileAction> findFileActionList = new ArrayList<>();

        File[] files = filePath.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            //
            if (file.isDirectory()) {
                findFileActionList.add(new FindFileAction(file));
                continue;
            }
            if (file.getAbsolutePath().endsWith("txt")) {
                System.out.println("文件" + file.getAbsolutePath());
            }
        }

        if (!findFileActionList.isEmpty()) {
            Collection<FindFileAction> actionList = invokeAll(findFileActionList);
            //
//            for (FindFileAction action : actionList) {
//                action.join();//汇总
//            }
        }
    }
}
