package com.sq.sell.service.impl;

import com.sq.sell.dto.CarDTO;
import com.sq.sell.entity.ProductInfo;
import com.sq.sell.enums.ProductStatusEnum;
import com.sq.sell.enums.ResultEnum;
import com.sq.sell.exception.SellException;
import com.sq.sell.repository.ProductRepository;
import com.sq.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductInfo findOne(String productId) {
        return productRepository.findById(productId).get();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productRepository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CarDTO> carDTOList) {

        for(CarDTO carDTO : carDTOList)
        {
            ProductInfo productInfo = productRepository.findById(carDTO.getProductId()).get();
            if(productInfo == null)
            {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            Integer result = productInfo.getProductStock() + carDTO.getProductQuantity();
            productInfo.setProductStock(result);
            productRepository.save(productInfo);
        }



    }

    /**
     * 记录一下getone和findone的区别
     * getone无法正常运行
     * 在高并发的情况下，可能会出现多扣库存的超卖情况
     * 需要进一步进行多线程优化
     * @param carDTOList
     */

    @Override
    @Transactional
    public void decrease(List<CarDTO> carDTOList) {
        for (CarDTO carDTO : carDTOList)
        {
            ProductInfo productInfo = productRepository.findById(carDTO.getProductId()).get();
            if(productInfo == null)
            {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            Integer result = productInfo.getProductStock() - carDTO.getProductQuantity();
            if(result < 0)
            {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productRepository.save(productInfo);
        }
    }
}
