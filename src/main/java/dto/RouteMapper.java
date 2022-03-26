package dto;

import domain.model.RouteMetaData;
import domain.model.RoutePosition;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface RouteMapper {
    RouteMapper INSTANCE = Mappers.getMapper(RouteMapper.class);

    @Mapping(target = "positions", source = "csvRouteDto.points")
    @Mapping(target = "shipId", source = "csvRouteDto.id")
    RouteMetaData toDomain(CsvRouteDto csvRouteDto);

    List<RouteMetaData> toDomain(List<CsvRouteDto> csvRouteDto);

    RoutePosition toDomain(CsvRoutePointDto csvRoutePointDto);

    @AfterMapping
    default void bindDomain(@MappingTarget RouteMetaData routeMetaData) {
        routeMetaData.getPositions().forEach(position -> position.setMetaData(routeMetaData));
    }

    default List<RouteMetaData> toDomainWithAccuracy(List<CsvRouteDto> csvRouteDto, double positionAccuracy) {
        csvRouteDto.stream().map(CsvRouteDto::getPoints).flatMap(Collection::stream).forEach(csvRoutePointDto -> csvRoutePointDto.setPositionAccuracy(positionAccuracy));
        return toDomain(csvRouteDto);
    }

}
