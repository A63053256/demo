package com.bean;

public class Result {
    private Object value;
    private String msg;
    private Boolean success;

    public Result(){
        this.success = true;
    }
    public boolean isSuccess(){
        return success;
    }
    @Override
    public String toString() {
        return "Result{" +
                "value=" + value +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                '}';
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
