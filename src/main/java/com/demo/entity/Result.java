package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    //定义响应码
    private Integer code;
    //定义提示信息
    private String message;
    //定义响应的数据
    private T data;

    public static <E> Result<E> success(E data){
        return new Result(0,"操作成功",data);
    }
    public static <E> Result<E> success(){
        return new Result(0,"操作成功",null);
    }
    public static <E> Result<E> error(String message){
        return  new Result(1,message,null);
    }
}
