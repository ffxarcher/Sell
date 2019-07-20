//package com.sq.sell.utils.serializer;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//
//import java.io.IOException;
//import java.util.Date;
//
///**
// * 使用
// * @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
// * @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
// * 本工具类暂时作废
// */
//public class Date2LongSerializer extends JsonSerializer<Date> {
//    @Override
//    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//         jsonGenerator.writeNumber(date.getTime()/1000);
//    }
//}
