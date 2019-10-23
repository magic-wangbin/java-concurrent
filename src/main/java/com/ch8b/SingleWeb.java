package com.ch8b;


import com.ch8b.assist.CreatePendingDocs;
import com.ch8b.assist.SL_QuestionBank;
import com.ch8b.service.ProduceDocService;
import com.ch8b.vo.SrcDocVo;

import java.util.List;

/**
 * 类说明：
 */
public class SingleWeb {
    public static void main(String[] args) {
        System.out.println("题库开始初始化...........");
        SL_QuestionBank.initBank();
        System.out.println("题库初始化完成。");

        //生成3分文档
        List<SrcDocVo> docList = CreatePendingDocs.makePendingDoc(3);

        long startTotal = System.currentTimeMillis();

        for (SrcDocVo doc : docList) {
            System.out.println("[" + doc.getDocName() + "]共有题目："
                + doc.getQuestionList().size() + "个");
            System.out.println("开始处理文档：" + doc.getDocName() + ".......");

            long start = System.currentTimeMillis();
            //开始生成文档
            String localName = ProduceDocService.makeDoc(doc);
            System.out.println("文档" + localName + "生成耗时：" + (System.currentTimeMillis() - start) + "ms");

            //上传文档
            start = System.currentTimeMillis();
            String remoteUrl = ProduceDocService.upLoadDoc(localName);
            System.out.println("已上传至[" + remoteUrl + "]耗时：" + (System.currentTimeMillis() - start) + "ms");
        }
        System.out.println("共耗时：" + (System.currentTimeMillis() - startTotal) + "ms");
    }
}
