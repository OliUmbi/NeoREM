import {Button, Content, Heading, Stack, TextInput} from "@carbon/react";
import {useAuthentication} from "../services/authentication";
import {useNavigate} from "react-router-dom";

const Login = () => {

    const authentication = useAuthentication()
    const navigate = useNavigate();

    const handleLogin = () => {
        authentication.login()
        navigate("/")
    }

    return (
        <Content>
            <Stack gap={8}>
                <Heading>Login</Heading>
                <Stack gap={4}>
                    <TextInput id="login-username"
                               labelText="Username"
                               required={true} />
                    <TextInput id="login-password"
                               labelText="Password"
                               type="password"
                               required={true} />
                </Stack>
                <Button onClick={handleLogin}>Login</Button>
            </Stack>
        </Content>
    )
}

export default Login
