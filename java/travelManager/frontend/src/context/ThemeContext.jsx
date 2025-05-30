import React, { createContext, useContext, useEffect } from 'react';

const ThemeContext = createContext({
    isDarkMode: false,
    toggleTheme: () => {}
});

export const ThemeProvider = ({ children }) => {
    useEffect(() => {
        document.documentElement.setAttribute('data-bs-theme', 'light');

        if (localStorage.getItem('theme') === 'dark') {
            localStorage.removeItem('theme');
        }
    }, []);

    return (
        <ThemeContext.Provider value={{ isDarkMode: false, toggleTheme: () => {} }}>
            {children}
        </ThemeContext.Provider>
    );
};

export const useTheme = () => useContext(ThemeContext);
