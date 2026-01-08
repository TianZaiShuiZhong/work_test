import baseURL from './config';

export default {
  trim(str) {
    return str.replace(/^\s*(.*?)\s*$/g, '$1');
  },
  getFilterArray(array) {
    const set = new Set(array);
    return [...set];
  },
  getImgUrl(url) {
    if (url && url.startsWith("http")) {
       return url;
    }
    // 检查url是否已经包含了/file/前缀，如果包含了，就不需要再加了？ 不，后端返回的是 /file/uuid.jpg
    // baseUrl 是 http://localhost:8080
    // 拼接结果是 http://localhost:8080//file/uuid.jpg  (注意双斜杠，虽然浏览器通常能处理)
    // 让我们规范化一下
    if (!url) return '';
    if (url.startsWith("/")) {
        return baseURL.baseUrl + url;
    }
    return baseURL.baseUrl + '/' + url;
  },
};
