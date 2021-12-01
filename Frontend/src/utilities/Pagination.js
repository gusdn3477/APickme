import React from "react";
import _ from "lodash";

const Pagination = (props) => {
  const { itemsCount, pageSize, currentPage, onPageChange } = props;

  const pageCount = Math.ceil(itemsCount / pageSize); // 몇 페이지가 필요한지 계산
  if (pageCount === 1) return null; // 1페이지 뿐이라면 페이지 수를 보여주지 않음

  const pages = _.range(1, pageCount + 1); // 마지막 페이지에 보여줄 컨텐츠를 위해 +1, https://lodash.com/docs/#range 참고

  return (
    <nav>
      {" "}
      {/* VSCode 입력: nav>ul.pagination>li.page-item>a.page-link */}
      <ul className="pagination justify-content-center pagination-lg">
        {pages.map((page) => (
          <li
            key={page}
            className={page === currentPage ? "page-item active" : "page-item"} // Bootstrap을 이용하여 현재 페이지를 시각적으로 표시
            style={{ cursor: "pointer" }}
          >
            <a className="page-link" onClick={() => onPageChange(page)}>
              {page}
            </a>{" "}
            {/* 페이지 번호 클릭 이벤트 처리기 지정 */}
          </li>
        ))}
      </ul>
    </nav>
  );
};

export default Pagination;
