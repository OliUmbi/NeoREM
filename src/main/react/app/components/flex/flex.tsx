import "./flex.scss";
import {ReactNode} from "react";

interface Props {
    children: ReactNode
    xl?: Flex
    lg?: Flex
    md?: Flex
    sm?: Flex
}

interface Flex {
    width?: "fit" | "full"
    height?: "fit" | "full"
    direction?: "row" | "column"
    align?: "start" | "center" | "end"
    justify?: "start" | "center" | "end" | "between" | "around" | "evenly"
    gap?: 0 | 0.5 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8
    wrap?: "never" | "always" | "reverse"
}

const Flex = (props: Props) => {

    return (
        <div className="flex"
             data-xl-width={props.xl?.width} data-xl-height={props.xl?.height} data-xl-direction={props.xl?.direction} data-xl-align={props.xl?.align} data-xl-justify={props.xl?.justify} data-xl-gap={props.xl?.gap} data-xl-wrap={props.xl?.wrap}
             data-lg-width={props.lg?.width} data-lg-height={props.lg?.height} data-lg-direction={props.lg?.direction} data-lg-align={props.lg?.align} data-lg-justify={props.lg?.justify} data-lg-gap={props.lg?.gap} data-lg-wrap={props.lg?.wrap}
             data-md-width={props.md?.width} data-md-height={props.md?.height} data-md-direction={props.md?.direction} data-md-align={props.md?.align} data-md-justify={props.md?.justify} data-md-gap={props.md?.gap} data-md-wrap={props.md?.wrap}
             data-sm-width={props.sm?.width} data-sm-height={props.sm?.height} data-sm-direction={props.sm?.direction} data-sm-align={props.sm?.align} data-sm-justify={props.sm?.justify} data-sm-gap={props.sm?.gap} data-sm-wrap={props.sm?.wrap}>
            {props.children}
        </div>
    )
}

export default Flex