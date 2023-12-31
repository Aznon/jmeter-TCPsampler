/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package JMeter.plugins.functional.samplers.socketsampler;

/**
 * Exception that contains partial response (Text read until exception occurred)
 */
public class ReadException extends Exception {

    private static final long serialVersionUID = -2770054697780959330L;
    private final String partialResponse;

    /**
     * @deprecated For use by test code only (serialisation tests)
     */
    @Deprecated
    public ReadException() {
        this(null, null, null);
    }

    /**
     * Constructor
     * @param message Message
     * @param cause Source cause
     * @param partialResponse  Text read until error occurred
     */
    public ReadException(String message, Throwable cause, String partialResponse) {
        super(message, cause);
        this.partialResponse = partialResponse;
    }

    /**
     * @return the partialResponse Text read until error occurred
     */
    public String getPartialResponse() {
        return partialResponse;
    }
}
