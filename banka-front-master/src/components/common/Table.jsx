import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";
import classNames from "classnames";
import Pagination from "./Pagination";

const borderColor = "border-gray-200"
const cellSpacing = "px-3 py-4"

function TableRow(props) {
  const cellClassnames = classNames("border-b text-gray-400 bg-white", borderColor, cellSpacing)

  if (!props.row) {
    return null
  }

  return (
    <tr>{props.row.map((r, j) => <td key={j} className={cellClassnames}>{r}</td>)}</tr>
  )
}

TableRow.propTypes = {
  row: PropTypes.arrayOf(PropTypes.any),
}

function Table(props) {
  const [startRange, setStartRange] = useState(0)
  const [endRange, setEndRange] = useState(0)

  const headerClassnames = classNames("border-b bg-gray-50", "px-3 py-1.5", borderColor)
  const tableClassnames = classNames(
    "border-collapse border", borderColor, // border
    "w-full", // width
    "text-left", // text
    props.classNames,
  );

  function renderRows() {
    const rows = []
    for (let i = startRange; i < endRange + 1; i++) {
      rows.push(<TableRow key={i} row={props.rows[i]} />)
    }
    return rows
  }

  function updateRanges(e) {
    if (!props.pagination) {
      return
    }

    setStartRange(e[0])
    setEndRange(e[1])
  }

  useEffect(() => {
    if (props.pagination) {
      setEndRange(props.paginationGroupSize - 1)
    } else {
      setEndRange(props.rows.length)
    }
  }, [props.rows, props.pagination, props.paginationGroupSize])

  return (
    <div>
      <div className="drop-shadow-md">
        <table className={tableClassnames}>
          <thead>
            <tr>
              {props.headings.map((h, i) => {
                return (
                  <th key={i} className={headerClassnames} id={h}>
                    {h}
                  </th>
                );
              })}
            </tr>
          </thead>
          <tbody>
            {renderRows().map(r => r)}
          </tbody>
        </table>
        {(props.rows.length === 0) &&
          <div className="border border-t-0 bg-white text-center py-10">{props.emptyStatePlaceholder}</div>
        }
      </div>
      {props.pagination &&
        <div className="mt-5 flex justify-center">
          <Pagination itemCount={props.rows.length} groupSize={props.paginationGroupSize} onChange={updateRanges} />
        </div>
      }
    </div>

  );
}

Table.propTypes = {
  headings: PropTypes.arrayOf(PropTypes.string),
  rows: PropTypes.array,
  emptyStatePlaceholder: PropTypes.string,
  pagination: PropTypes.bool,
  paginationGroupSize: PropTypes.number,
  classNames: PropTypes.string,
};

Table.defaultProps = {
  emptyStatePlaceholder: "No data.",
  pagination: false,
  paginationGroupSize: 5,
}

export default Table;
