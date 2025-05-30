import {createContext, useContext, useState} from "react";

const MessageContext = createContext();

export function MessageProvider({children}) {
    const [message, setMessage] = useState(null);
    const [messageType, setMessageType] = useState("success");

    const showMessage = (type, text) => {

        setMessageType(type);
        setMessage(text);

        setTimeout(() => {
            setMessage(null);
        }, 3000);
    };

    return (
        <MessageContext.Provider value={{message, messageType, showMessage}}>
            {children}
        </MessageContext.Provider>
    );
}

export function useMessage() {
    return useContext(MessageContext);
}