import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

export default function JobListForProcessDetail({ idx, key, data }) {
  // console.log("form" + data);
  return (
    <tr>
      <td>{idx}</td>
      <td>{data.applyName}</td>
      <td>{data.applyEmail}</td>
      <td>{data.applyContact}</td>
      <td>{data.writtenScore}</td>
      <td>{data.firstInterviewScore}</td>
      <td>{data.secondInterviewScore}</td>
    </tr>
  );
}
