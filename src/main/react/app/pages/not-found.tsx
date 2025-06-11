import {Button, Column, Content, Heading, Row, Stack} from "@carbon/react";
import {useLocation, useNavigate} from "react-router-dom";
import {ContentText} from "@carbon/react/es/components/PageHeader";
import Flex from "../components/flex/flex";

const NotFound = () => {

    const location = useLocation()
    const navigate = useNavigate()

    return (
        <Content>
            <Stack gap={8}>
                <Stack gap={4}>
                    <Heading>Not found</Heading>
                    <ContentText>We could not find the resource {location.pathname}</ContentText>
                </Stack>
                <Flex>
                    <Button onClick={() => navigate(-1)}>Go Back</Button>
                    <Button onClick={() => navigate("/")} kind="tertiary">Home</Button>
                </Flex>
            </Stack>
        </Content>
    )
}

export default NotFound
