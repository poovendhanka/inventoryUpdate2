function showAdditionalFields() {
    const productType = document.getElementById('productType').value;
    const fiberTypeDiv = document.getElementById('fiberTypeDiv');
    const pithTypeDiv = document.getElementById('pithTypeDiv');
    const blockDiv = document.getElementById('blockDiv');
    
    // Hide all first
    fiberTypeDiv.style.display = 'none';
    pithTypeDiv.style.display = 'none';
    blockDiv.style.display = 'none';
    
    // Show relevant div based on selection
    if (productType === 'FIBER_BROWN' || productType === 'FIBER_WHITE') {
        fiberTypeDiv.style.display = 'block';
    } else if (productType === 'PITH_REGULAR' || productType === 'PITH_LOW_EC') {
        pithTypeDiv.style.display = 'block';
    } else if (productType === 'BLOCK') {
        blockDiv.style.display = 'block';
    }
} 