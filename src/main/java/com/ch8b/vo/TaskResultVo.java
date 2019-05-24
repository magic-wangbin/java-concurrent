package com.ch8b.vo;

import java.util.concurrent.Future;

/**
 * 类说明：并发处理返回的题目结果实体类
 */
public class TaskResultVo {
    private final String questionDetail;
    private final Future<QuestionInCacheVo> questionFuture;

    //缓存已经存在
    public TaskResultVo(String questionDetail) {
        this.questionDetail = questionDetail;
        this.questionFuture = null;
    }

    //缓存中不存在
    public TaskResultVo(Future<QuestionInCacheVo> questionFuture) {
        this.questionFuture = questionFuture;
        this.questionDetail = null;
    }

    public String getQuestionDetail() {
        return questionDetail;
    }

    public Future<QuestionInCacheVo> getQuestionFuture() {
        return questionFuture;
    }
}
