import {createContext, ReactNode, useContext, useState} from 'react';

const AuthenticationContext = createContext({
    isAuthenticated: false,
    login: () => {}
})

export const AuthenticationProvider = ({ children }: { children: ReactNode }) => {
    const [authenticated, setAuthenticated] = useState<boolean>(false)
    const login = () => setAuthenticated(true)

    return <AuthenticationContext.Provider value={{isAuthenticated: authenticated, login: login}}>{children}</AuthenticationContext.Provider>
}

export const useAuthentication = () => useContext(AuthenticationContext)
