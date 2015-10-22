package bai4_rn.praktikum_01.command;

import bai4_rn.praktikum_01.client.ClientData;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Andreas on 22.10.2015.
 */
public enum ClientResponseEnum implements ClientResponse {
    HELO {
        @Override
        public void process() {

        }

        @Override
        public List<ClientResponse> getPossibleSuccessors() {
            return null;
        }
    },

    EHLO {
        @Override
        public void process() {

        }

        @Override
        public List<ClientResponse> getPossibleSuccessors() {
            return null;
        }
    },

    AUTH {
        @Override
        public void process() {

        }

        @Override
        public List<ClientResponse> getPossibleSuccessors() {
            return null;
        }
    },

    MAIL {
        @Override
        public void process() {

        }

        @Override
        public List<ClientResponse> getPossibleSuccessors() {
            return null;
        }
    },

    RCPT {
        @Override
        public void process() {

        }

        @Override
        public List<ClientResponse> getPossibleSuccessors() {
            return null;
        }
    },

    DATA {
        @Override
        public void process() {

            String from;
            String to;
            String subject;
            String mimeVersion;
            String ContentType;
        }

        @Override
        public List<ClientResponse> getPossibleSuccessors() {
            return null;
        }
    },

    QUIT {
        @Override
        public void process() {

        }

        @Override
        public List<ClientResponse> getPossibleSuccessors() {
            return null;
        }
    };


    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String toString() {
        return "ClientResponse{" +
                "name='" + getName() + '\'' +
                '}';
    }

}
