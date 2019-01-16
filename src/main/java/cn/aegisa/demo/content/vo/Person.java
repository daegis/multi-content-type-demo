package cn.aegisa.demo.content.vo;

import lombok.Data;

/**
 * @author xianyingda@guazi.com
 * @serial
 * @since 2019-01-14 15:54
 */
@Data
public class Person implements PostEntity {
    private String name;
    private Integer age;
}
