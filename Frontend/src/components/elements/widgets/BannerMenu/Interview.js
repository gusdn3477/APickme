import React from 'react';
import { Link } from 'react-router-dom';
import "bootstrap-icons/font/bootstrap-icons.css";

export default function Interview() {

  return (
    <li className="nav-item">
      <a className="nav-link" data-bs-toggle="collapse" href="#interview-control" aria-expanded="false" aria-controls="interview-control">
        <i className="icon-layout menu-icon"></i>
        <span className="menu-title">면접전형관리(인사담당자)</span>
        <i className="menu-arrow"></i>
      </a>
      <div className="collapse" id="interview-control">
        <ul className="nav flex-column sub-menu">
          <li className="nav-item"><Link className="nav-link" to="/process/interview">면접 전형 관리</Link></li>
        </ul>
      </div>
    </li>

  );
}