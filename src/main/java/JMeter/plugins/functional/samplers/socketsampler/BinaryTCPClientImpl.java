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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;

import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.util.JOrphanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TCPClient implementation.
 * Reads data until the defined EOM byte is reached.
 * If there is no EOM byte defined, then reads until
 * the end of the stream is reached.
 * The EOM byte is defined by the property "tcp.BinaryTCPClient.eomByte".
 *
 * <p>Input/Output strings are passed as hex-encoded binary strings.</p>
 */
public class BinaryTCPClientImpl extends AbstractTCPClient {
    private static final Logger log = LoggerFactory.getLogger(BinaryTCPClientImpl.class);

    private static final int EOM_INT = JMeterUtils.getPropDefault("tcp.BinaryTCPClient.eomByte", 1000); // $NON_NLS-1$

    public BinaryTCPClientImpl() {
        super();
        setEolByte(EOM_INT);
        if (useEolByte) {
            log.info("Using eomByte={}", eolByte);
        }
    }

    /**
     * Convert hex string to binary byte array.
     *
     * @param hexEncodedBinary - hex-encoded binary string
     * @return Byte array containing binary representation of input hex-encoded string
     * @throws IllegalArgumentException if string is not an even number of hex digits
     */
    public static byte[] hexStringToByteArray(String hexEncodedBinary) {
        if (hexEncodedBinary.length() % 2 == 0) {
            char[] sc = hexEncodedBinary.toCharArray();
            byte[] ba = new byte[sc.length / 2];

            for (int i = 0; i < ba.length; i++) {
                int nibble0 = Character.digit(sc[i * 2], 16);
                int nibble1 = Character.digit(sc[i * 2 + 1], 16);
                if (nibble0 == -1 || nibble1 == -1){
                    throw new IllegalArgumentException(
                    "Hex-encoded binary string contains an invalid hex digit in '"+sc[i * 2]+sc[i * 2 + 1]+"'");
                }
                ba[i] = (byte) ((nibble0 << 4) | nibble1);
            }

            return ba;
        } else {
            throw new IllegalArgumentException(
                    "Hex-encoded binary string contains an uneven no. of digits");
        }
    }

    /**
     * Input (hex) string is converted to binary and written to the output stream.
     * @param os output stream
     * @param hexEncodedBinary hex-encoded binary
     */
    @Override
    public void write(OutputStream os, String hexEncodedBinary) throws IOException{
        os.write(hexStringToByteArray(hexEncodedBinary));
        os.flush();
        if(log.isDebugEnabled()) {
            log.debug("Wrote: " + hexEncodedBinary);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(OutputStream os, InputStream is) {
        throw new UnsupportedOperationException(
                "Method not supported for Length-Prefixed data.");
    }

    @Deprecated
    public String read(InputStream is) throws ReadException {
        log.warn("Deprecated method, use read(is, sampleResult) instead");
        return read(is, new SampleResult());
    }

    /**
     * Reads data until the defined EOM byte is reached.
     * If there is no EOM byte defined, then reads until
     * the end of the stream is reached.
     * Response data is converted to hex-encoded binary
     * @return hex-encoded binary string
     * @throws ReadException when reading fails
     */
    @Override
    public String read(InputStream is, SampleResult sampleResult) throws ReadException {
        ByteArrayOutputStream w = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[4096];
            int x = 0;
            boolean first = true;
            while ((x = is.read(buffer)) > -1) {
                if (first) {
                    sampleResult.latencyEnd();
                    first = false;
                }
                w.write(buffer, 0, x);
                if (useEolByte && (buffer[x - 1] == eolByte)) {
                    break;
                }
            }
        } catch (SocketTimeoutException e) {
            if (useEolByte) {
                throw new ReadException("Socket timed out while looking for EOM", e,
                        JOrphanUtils.baToHexString(w.toByteArray()));
            }
            log.debug("Ignoring SocketTimeoutException, as we are not looking for EOM", e);
        } catch (IOException e) {
            throw new ReadException("Problems while trying to read", e, JOrphanUtils.baToHexString(w.toByteArray()));
        }
        final String hexString = JOrphanUtils.baToHexString(w.toByteArray());
        if(log.isDebugEnabled()) {
            log.debug("Read: {}\n{}", w.size(), hexString);
        }
        return hexString;
    }

}
