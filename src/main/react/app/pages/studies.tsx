import {
    DataTable,
    Table,
    TableHead,
    TableRow,
    TableHeader,
    TableBody,
    TableCell,
    Column,
    Pagination,
    DatePicker,
    DatePickerInput,
    Row,
    Form
} from "@carbon/react";
import {useState} from "react";
import Flex from "../components/flex/flex";

const Studies = () => {

    const [page, setPage] = useState<number>(1);

    const headers = [
        {
            key: "date",
            header: "Date",
        },
        {
            key: "institution",
            header: "Institution",
        },
        {
            key: "description",
            header: "Description",
        },
        {
            key: "events",
            header: "Events",
        },
        {
            key: "machine",
            header: "Machine",
        },
        {
            key: "totalDlp",
            header: "Total DLP",
        },
    ]

    const rows = [
        {
            id: "1",
            date: "2024.07.23 14:56",
            institution: "Kantonsspital Aarau",
            description: "Chest_Abdomen (Adult)",
            events: "3",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "818.77",
        },
        {
            id: "2",
            date: "2024.07.23 12:23",
            institution: "Kantonsspital Aarau",
            description: "Thorax^Chest_Liver (Adult)",
            events: "4",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "837.73",
        },
        {
            id: "3",
            date: "2024.07.23 09:08",
            institution: "Kantonsspital Aarau",
            description: "Brain (Adult)",
            events: "2",
            machine: "PHILIPS, Brilliance 64",
            totalDlp: "1276.57",
        },
        {
            id: "4",
            date: "2024.07.22 18:34",
            institution: "Kantonsspital Aarau",
            description: "Chest_Abdomen (Adult)",
            events: "3",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "1007.65",
        },
        {
            id: "5",
            date: "2024.07.22 15:48",
            institution: "Kantonsspital Aarau",
            description: "CT Head",
            events: "3",
            machine: "PHILIPS, Brilliance 64",
            totalDlp: "839.88",
        },
        {
            id: "6",
            date: "2024.07.22 11:04",
            institution: "Kantonsspital Aarau",
            description: "Thorax^Chest_Liver (Adult)",
            events: "4",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "724.76",
        },
        {
            id: "1",
            date: "2024.07.23 14:56",
            institution: "Kantonsspital Aarau",
            description: "Chest_Abdomen (Adult)",
            events: "3",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "818.77",
        },
        {
            id: "2",
            date: "2024.07.23 12:23",
            institution: "Kantonsspital Aarau",
            description: "Thorax^Chest_Liver (Adult)",
            events: "4",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "837.73",
        },
        {
            id: "3",
            date: "2024.07.23 09:08",
            institution: "Kantonsspital Aarau",
            description: "Brain (Adult)",
            events: "2",
            machine: "PHILIPS, Brilliance 64",
            totalDlp: "1276.57",
        },
        {
            id: "4",
            date: "2024.07.22 18:34",
            institution: "Kantonsspital Aarau",
            description: "Chest_Abdomen (Adult)",
            events: "3",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "1007.65",
        },
        {
            id: "5",
            date: "2024.07.22 15:48",
            institution: "Kantonsspital Aarau",
            description: "CT Head",
            events: "3",
            machine: "PHILIPS, Brilliance 64",
            totalDlp: "839.88",
        },
        {
            id: "6",
            date: "2024.07.22 11:04",
            institution: "Kantonsspital Aarau",
            description: "Thorax^Chest_Liver (Adult)",
            events: "4",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "724.76",
        },
        {
            id: "1",
            date: "2024.07.23 14:56",
            institution: "Kantonsspital Aarau",
            description: "Chest_Abdomen (Adult)",
            events: "3",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "818.77",
        },
        {
            id: "2",
            date: "2024.07.23 12:23",
            institution: "Kantonsspital Aarau",
            description: "Thorax^Chest_Liver (Adult)",
            events: "4",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "837.73",
        },
        {
            id: "3",
            date: "2024.07.23 09:08",
            institution: "Kantonsspital Aarau",
            description: "Brain (Adult)",
            events: "2",
            machine: "PHILIPS, Brilliance 64",
            totalDlp: "1276.57",
        },
        {
            id: "4",
            date: "2024.07.22 18:34",
            institution: "Kantonsspital Aarau",
            description: "Chest_Abdomen (Adult)",
            events: "3",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "1007.65",
        },
        {
            id: "5",
            date: "2024.07.22 15:48",
            institution: "Kantonsspital Aarau",
            description: "CT Head",
            events: "3",
            machine: "PHILIPS, Brilliance 64",
            totalDlp: "839.88",
        },
        {
            id: "6",
            date: "2024.07.22 11:04",
            institution: "Kantonsspital Aarau",
            description: "Thorax^Chest_Liver (Adult)",
            events: "4",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "724.76",
        },
        {
            id: "1",
            date: "2024.07.23 14:56",
            institution: "Kantonsspital Aarau",
            description: "Chest_Abdomen (Adult)",
            events: "3",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "818.77",
        },
        {
            id: "2",
            date: "2024.07.23 12:23",
            institution: "Kantonsspital Aarau",
            description: "Thorax^Chest_Liver (Adult)",
            events: "4",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "837.73",
        },
        {
            id: "3",
            date: "2024.07.23 09:08",
            institution: "Kantonsspital Aarau",
            description: "Brain (Adult)",
            events: "2",
            machine: "PHILIPS, Brilliance 64",
            totalDlp: "1276.57",
        },
        {
            id: "4",
            date: "2024.07.22 18:34",
            institution: "Kantonsspital Aarau",
            description: "Chest_Abdomen (Adult)",
            events: "3",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "1007.65",
        },
        {
            id: "5",
            date: "2024.07.22 15:48",
            institution: "Kantonsspital Aarau",
            description: "CT Head",
            events: "3",
            machine: "PHILIPS, Brilliance 64",
            totalDlp: "839.88",
        },
        {
            id: "6",
            date: "2024.07.22 11:04",
            institution: "Kantonsspital Aarau",
            description: "Thorax^Chest_Liver (Adult)",
            events: "4",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "724.76",
        },
        {
            id: "1",
            date: "2024.07.23 14:56",
            institution: "Kantonsspital Aarau",
            description: "Chest_Abdomen (Adult)",
            events: "3",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "818.77",
        },
        {
            id: "2",
            date: "2024.07.23 12:23",
            institution: "Kantonsspital Aarau",
            description: "Thorax^Chest_Liver (Adult)",
            events: "4",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "837.73",
        },
        {
            id: "3",
            date: "2024.07.23 09:08",
            institution: "Kantonsspital Aarau",
            description: "Brain (Adult)",
            events: "2",
            machine: "PHILIPS, Brilliance 64",
            totalDlp: "1276.57",
        },
        {
            id: "4",
            date: "2024.07.22 18:34",
            institution: "Kantonsspital Aarau",
            description: "Chest_Abdomen (Adult)",
            events: "3",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "1007.65",
        },
        {
            id: "5",
            date: "2024.07.22 15:48",
            institution: "Kantonsspital Aarau",
            description: "CT Head",
            events: "3",
            machine: "PHILIPS, Brilliance 64",
            totalDlp: "839.88",
        },
        {
            id: "6",
            date: "2024.07.22 11:04",
            institution: "Kantonsspital Aarau",
            description: "Thorax^Chest_Liver (Adult)",
            events: "4",
            machine: "SIEMENS, SOMATOM Definition 64",
            totalDlp: "724.76",
        },
    ]

    return (
        <Flex xl={{direction: "row", gap: 2}} lg={{direction: "column"}}>
            <Flex xl={{direction: "column", width: "fit", height: "fit", gap: 1}}>
                <DatePicker
                    datePickerType="single"
                    onChange={() => {
                    }}
                    onClose={() => {
                    }}
                    onOpen={() => {
                    }}>
                    <DatePickerInput
                        id="studies-from"
                        labelText="From"
                        onChange={() => {
                        }}
                        onClose={() => {
                        }}
                        onOpen={() => {
                        }}
                        placeholder="yyyy.mm.dd"
                    />
                </DatePicker>
                <DatePicker
                    datePickerType="single"
                    onChange={() => {
                    }}
                    onClose={() => {
                    }}
                    onOpen={() => {
                    }}>
                    <DatePickerInput
                        id="studies-to"
                        labelText="To"
                        onChange={() => {
                        }}
                        onClose={() => {
                        }}
                        onOpen={() => {
                        }}
                        placeholder="yyyy.mm.dd"
                    />
                </DatePicker>
            </Flex>
            <Flex xl={{direction: "column", width: "full", height: "fit"}}>
                {/* @ts-ignore */}
                <DataTable rows={rows} headers={headers} isSortable={true} size="lg">
                    {({rows, headers, getTableProps, getHeaderProps, getRowProps}) => (
                        <Table {...getTableProps()}>
                            <TableHead>
                                <TableRow>
                                    {headers.map((header) => (
                                        <TableHeader {...getHeaderProps({header})}>
                                            {header.header}
                                        </TableHeader>
                                    ))}
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {rows.map((row) => (
                                    <TableRow {...getRowProps({row})}>
                                        {row.cells.map((cell) => (
                                            <TableCell key={cell.id}>{cell.value}</TableCell>
                                        ))}
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    )}
                </DataTable>
                <Pagination
                    page={page}
                    pageSize={10}
                    pageSizes={[10, 20, 30, 40, 50]}
                    totalItems={100}
                    onChange={({page}) => setPage(page)}
                />
            </Flex>
        </Flex>
    )
}

export default Studies
