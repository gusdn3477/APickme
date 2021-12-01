import { ChangeEmpNoString } from "../../../../utilities/ChangeString";

export default function WrittenTableForm({ idx, key, data, jobsNo}) {

  const name2 = ChangeEmpNoString(data.writtenCheck)
  return (
    <tr>
      <td>{idx}</td>
      <td>{data.applyNum ? (data.applyNum).substring(0,8) : ""}</td>
      <td>{data.writtenScore}</td>
      <td>{data.writtenResult}</td>
      <td>{data.writtenCheck ? name2 : ""}</td>
      {/* <td>{data.empNo ? (data.empNo).substring(0,8) : ""}</td> */}
    </tr>
  );
}