# 使用参数校验

## 在Bean类中需要参数校验的成员变量上添加注解

```java
	// URL或域名
	// message为自定义的错误信息
    @Pattern(regexp = "[a-zA-z]+://[^\\s]*|[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?",
            message = "请输入正确的活动链接")
	
    private String link;

```

## 在Controller层中的方法上增加`@ParamValidation`注解，在形参前增加@Validated注解，在最后增加形参 `BindingResult bindingResult`

```java
@ParamValidation
    @PostMapping("/create")
    public BaseRespVo create(@RequestBody @Validated MarketAd ad, BindingResult bindingResult) {
```





> ```java
> 常见的注解 （Bean Validation 中内置的 constraint）     
> @Null   被注释的元素必须为 null     
> @NotNull    被注释的元素必须不为 null     
> @Size(max=, min=)   被注释的元素的大小必须在指定的范围内     
> @AssertTrue     被注释的元素必须为 true     
> @AssertFalse    被注释的元素必须为 false     
> @Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值     
> @Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值     
> @DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值     
> @DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值     
> @Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内     
> @Past   被注释的元素必须是一个过去的日期 Date    
> @Future     被注释的元素必须是一个将来的日期     
> @Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式     
> Hibernate Validator 附加的 constraint     
> @NotBlank(message =)   验证字符串非null，且长度必须大于0     
> @Email  被注释的元素必须是电子邮箱地址     
> @Length(min=,max=)  被注释的字符串的大小必须在指定的范围内     
> @NotEmpty   被注释的字符串的必须非空     
> @Range(min=,max=,message=)  被注释的元素必须在合适的范围内
> ```
>
> 

