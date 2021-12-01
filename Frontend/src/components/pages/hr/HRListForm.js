import { useState, useEffect } from "react";

export default function HRListForm({ idx, key, data, setMyList }) {

  const [parents, setParents] = useState();

  useEffect(() => {
    fetch(`/hr-service/hr/detail/${localStorage.getItem('empNo')}`)
      .then(res => {
        return res.json();
      })
      .then(data => {
        setParents(data);
      });
  }, []);

  const handleDelete = () => {

    fetch(`/hr-service/hr/super`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        parents: parents.parents,
        empNo: data.empNo // 확실하지 않음 되면 이걸로 사용
      })
    }).then(
      alert("삭제 되었습니다!"),
      fetch(`/hr-service/hr/${localStorage.getItem('empNo')}`)
        .then(res => {
          return res.json();
        })
        .then(data => {
          setMyList(data);
        })
    )
  }

  const useConfirm = (message = null, onConfirm, onCancel, deleteHR) => {
    if (!onConfirm || typeof onConfirm !== "function") {
      return;
    }
    if (onCancel && typeof onCancel !== "function") {
      return;
    }

    const confirmAction = () => {
      if (window.confirm(message)) {
        onConfirm();
        deleteHR();
      } else {
        onCancel();
      }
    };

    return confirmAction;
  };

  const deleteConfirm = () => 1;
  const cancelConfirm = () => 0;
  const confirmDelete = useConfirm(
    "삭제하시겠습니까?",
    deleteConfirm,
    cancelConfirm,
    handleDelete
  );

  return (
    <tr>
      <td>{idx}</td>
      <td>{data.name}</td>
      <td className="font-weight-bold">{data.email}</td>
      <td>{data.empNo}</td>
      <td className="font-weight-bold">{data.parents === "admin" ? "슈퍼" : "일반"}</td>
      <td className="font-weight-bold" style={{textAlign:"center"}}>{data.auth === "true" ? "허가" : "불허"}</td>
      <td className="font-weight-medium" style={{textAlign:"center"}}><button type="button" class="btn btn-outline-danger" onClick={() => confirmDelete()}>삭제하기</button></td>
    </tr>
  );
}