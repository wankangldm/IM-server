package top.wankang.imServer.model;

public class Chat {
    // 消息的目标用户
    private String to;
    // 消息的来源用户
    private String from;
    // 消息的主体内容
    private String content;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}