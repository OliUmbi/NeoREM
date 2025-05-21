import {Column, Grid, Heading, Row, Section, Stack, Switch, Toggle} from "@carbon/react";

const Configurations = () => {

    return (
        <Stack gap={8}>
            <Heading>Configurations</Heading>
            <Section>
                <Stack gap={4}>
                    <Heading>Security</Heading>
                    <Toggle id="settings-security-encrypt-patient-id"
                            labelText="Encrypt Patient ID"
                            labelA="Disabled"
                            labelB="Enabled"/>
                </Stack>
            </Section>
            <Section>
                <Heading>E-Mail</Heading>
            </Section>
            <Section>
                <Heading>DICOM Network</Heading>
            </Section>
        </Stack>
    )
}

export default Configurations
