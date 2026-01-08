package org.example.shop.goods.service;

import org.example.shop.goods.dao.GoodRepository;
import org.example.shop.goods.dto.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    private GoodRepository goodRepository;

    /**
     * 单个商品信息
     * @param id 商品ID
     * @return 商品信息
     */
    public Good findGoodsById(int id) {
        return goodRepository.findById(id).orElse(null);
    }

    /**
     * 通过名称查找商品
     * @param name 商品名称
     * @return 商品信息
     */
    public Good findGoodsByName(String name) {
        return goodRepository.findByName(name);
    }

    public List<Good> findGoodsByPage(PageRequest pageRequest) {
        return goodRepository.findAll(pageRequest).getContent();
    }

    public List<Good> findGoodsByPageAndColor(String color, PageRequest pageRequest) {
        return goodRepository.findAllByColor(color, pageRequest).getContent();
    }

    public List<Good> findGoodsByPageAndBrand(String brand, PageRequest pageRequest) {
        return goodRepository.findAllByBrand(brand, pageRequest).getContent();
    }

    public List<Good> findGoodsByPageAndColorAndBrand(String color, String brand, PageRequest pageRequest) {
        return goodRepository.findAllByColorAndBrand(color, brand, pageRequest).getContent();
    }

    /**
     * 添加商品
     * @param good 商品信息
     * @return 结果
     */
    public boolean saveGood(Good good) {
        good.setSales(0);
        good.setState(1);
        goodRepository.save(good);
        return true;
    }

    /**
     * 修改商品
     * @param good 商品信息
     * @return 结果
     */
    public boolean updateGood(Good good) {
        goodRepository.save(good);
        return true;
    }

    /**
     * 删除商品
     * @param id 商品ID
     * @return 结果
     */
    public boolean deleteGood(int id) {
        goodRepository.deleteById(id);
        return true;
    }

    /**
     * 商品上下架
     * @param good 商品信息
     * @return 结果
     */
    public boolean changeGoodState(Good good) {
        Good g = findGoodsById(good.getId());
        if (g != null) {
            g.setState(good.getState());
            goodRepository.save(g);
            return true;
        }
        return false;
    }
}
