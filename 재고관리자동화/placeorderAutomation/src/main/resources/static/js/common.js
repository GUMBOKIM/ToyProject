function removeAllNode(cell) {
    while (cell.hasChildNodes()) {
        cell.removeChild(cell.firstChild);
    }
    cell.remove();
}

function removeChildNode(cell) {
    while (cell.hasChildNodes()) {
        cell.removeChild(cell.firstChild);
    }
}
