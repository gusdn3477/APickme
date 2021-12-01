import React from 'react';
import { Link } from 'react-router-dom';
import "bootstrap-icons/font/bootstrap-icons.css";

export default function FindJobs() {

  return (
    <li className="nav-item">
      <a className="nav-link" data-bs-toggle="collapse" href="#jobs-find" aria-expanded="false" aria-controls="jobs-find">
        <i className="icon-layout menu-icon"></i>
        <span className="menu-title">공고보기</span>
        <i className="menu-arrow"></i>
      </a>
      <div className="collapse" id="jobs-find">
        <ul className="nav flex-column sub-menu">
          <li className="nav-item"><Link className="nav-link" to="/jobs">공고목록</Link></li>
        </ul>
      </div>
    </li>

  );
}