/*
 *   Copyright (C) 2024-2025  DeviceLinks
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.devicelinks.framework.common.http;

import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.jackson2.DeviceLinksJsonMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;

/**
 * The {@link ApiResponse} Message Converter
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class ApiResponseHttpMessageConverter extends AbstractHttpMessageConverter<ApiResponse<?>> {
    private final DeviceLinksJsonMapper jsonMapper;

    public ApiResponseHttpMessageConverter() {
        super(MediaType.APPLICATION_JSON, new MediaType("application", "*+json"));
        this.jsonMapper = new DeviceLinksJsonMapper();
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return ApiResponse.class.isAssignableFrom(clazz);
    }

    @Override
    protected ApiResponse<?> readInternal(Class<? extends ApiResponse<?>> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return jsonMapper.readValue(inputMessage.getBody(), ApiResponse.class);
    }

    @Override
    protected void writeInternal(ApiResponse<?> apiResponse, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        try {
            jsonMapper.writeValue(outputMessage.getBody(), apiResponse);
        } catch (Exception ex) {
            throw new HttpMessageNotWritableException(
                    "An error occurred writing the ApiResponse: " + ex.getMessage(), ex);
        }
    }
}
