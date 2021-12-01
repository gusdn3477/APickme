export default function SearchFormView({data}) {
    return(
        <tr>
            <td className="product-thumbnail">
                <img className="img-fluid" src="" alt=""/>
            </td>
            <td className="product-name">
                <a href="/product/2">{data.productId}</a>
            </td>
            <td className="product-price-cart">
                <span className="amount">{data.productName}</span>
            </td>
            <td className="product-price-cart">
                <span className="amount">{data.stock}</span>
            </td>
            <td className="product-price-cart">
                <span className="amount">{data.unitPrice}</span>
            </td>
            <td className="product-price-cart">
                <span className="amount">{data.writer}</span>
            </td>
        </tr>
    );
}