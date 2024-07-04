/**
 * 密钥：必须16位
 */
var keyStr = "0123456789ABCDEF";
var key = CryptoJS.enc.Utf8.parse(keyStr);

/**
 * 加密（需要先加载crypto-js.js文件）
 * @param content
 * @returns {*}
 */
function encrypt(content){
  let srcs = CryptoJS.enc.Utf8.parse(content);
  let encrypted = CryptoJS.AES.encrypt(srcs, key, {mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7});
  return encrypted.toString();
}

/**
 * 解密
 * @param content
 * @returns {*}
 */
function decrypt(content){
  let decrypt = CryptoJS.AES.decrypt(content, key, {mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7});
  return CryptoJS.enc.Utf8.stringify(decrypt).toString();
}