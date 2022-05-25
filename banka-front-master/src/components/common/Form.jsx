import PropTypes from "prop-types";
import Button from "./Button";
import TextField from "./TextField";
import classNames from "classnames";

function Form(props) {

    const infoClasses = classNames(
        "pt-4 pl-12 pr-12",
        "text-center"
    )

    const linkClasses = classNames(
        "pr-12 pt-8",
        "text-right underline text-blue-600"
    )

    const fieldClasses = classNames(
        "pt-8 pl-12 pr-12"
    )

    const formWrapperClasses = classNames(
        "box-border",
        "h-full w-[500px]",
        "border-1",
        "bg-gray-50"
    )

    const tittleClasses = classNames(
        "box-border",
        "h-full w-full",
        "text-2xl text-white",
        "pt-4 pb-4 pl-12 pr-12",
        "border-1",
        "bg-gray-500"
    )

    const buttonWrapperClasses = classNames(
        'pt-8 pb-8 pl-12 pr-12'
    )

    function renderInfo() {
        if (props.info)
            return <div className={infoClasses}>{props.info}</div>
        return;
    }

    function renderLink() {
        if (props.link)
            return <div className={linkClasses}><a href={props.link[1]}>{props.link[0]}</a></div>
        return;
    }

    function renderFields() {
        const fields = []
        props.fields.map(item => {
            fields.push(<p className={fieldClasses}><TextField placeholder={item[0]} type={item[1]} className='w-full' /></p>)
        })
        return fields
    }

    return (
        <div className={formWrapperClasses}>
            <div className={tittleClasses}>
                {props.title}
            </div>
            <div>
                {renderInfo()}
            </div>
            <form>
                <div>
                    {renderFields().map(r => r)}
                </div>
                <div>
                    {renderLink()}
                </div>
                <div className={buttonWrapperClasses}>
                    <Button label={props.button} className='w-full' />
                </div>
            </form>
        </div>


    );
}

Form.propTypes = {
    title: PropTypes.string,
    info: PropTypes.string,
    fields: PropTypes.arrayOf(PropTypes.array),
    link: PropTypes.arrayOf(PropTypes.string),
    button: PropTypes.string
};

export default Form;
