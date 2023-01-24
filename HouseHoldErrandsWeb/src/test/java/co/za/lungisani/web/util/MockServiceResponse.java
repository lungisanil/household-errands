package co.za.lungisani.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class MockServiceResponse {

    private MockServiceResponse(){}

    public static <T> T mockServiceResponse(final String filePath, final Class<T> classType) throws Exception {
        return new ObjectMapper().readValue(new File(filePath), classType);
    }

}
